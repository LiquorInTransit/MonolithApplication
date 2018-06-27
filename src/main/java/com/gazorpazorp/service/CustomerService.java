package com.gazorpazorp.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import com.gazorpazorp.LITMonolith.config.LITSecurityUtil;
import com.gazorpazorp.client.ImgurClient;
import com.gazorpazorp.model.Customer;
import com.gazorpazorp.model.UserPrincipal;
import com.gazorpazorp.model.dto.CustomerInfoUpdateDto;
import com.gazorpazorp.model.imgur.ImgurResp;
import com.gazorpazorp.repository.CustomerRepository;
import com.stripe.net.RequestOptions;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	ImgurClient imgurClient;
	
	@Value("${stripe.secret-key}")
	String secretKey;
	
	private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public Customer getCurrentCustomer () {
//		Map<String, Object> additionalInfo = getExtraInfo(SecurityContextHolder.getContext().getAuthentication());
//		Long id = Long.parseLong(additionalInfo.get("userId").toString());//Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		Long id = LITSecurityUtil.currentUser().getUserId();
		Customer customer = customerRepo.findByUserId(id);
		return customer;
	}
	public Map<String, Object> getExtraInfo (Authentication auth) {
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		return (Map<String, Object>) oauthDetails.getDecodedDetails();
	}
	
	public Customer createCustomer (Customer customer, String email) {
		String stripeId = createNewStripeCustomer(email);
		if (stripeId==null)
			return null;
		customer.setStripeId(stripeId);
		return customerRepo.save(customer);
	}	
	public void deleteCustomerByUserId(Long userId) {
		customerRepo.deleteByUserId(userId);
	}
	
	public Customer updateCustomer(CustomerInfoUpdateDto dto) {
		Customer customer = getCurrentCustomer();
		if (customer != null && dto != null) {
			dto.Incorporate(customer);
			try {
				ImgurResp resp = uploadFile(dto.getFile());
				if (resp != null) {
					customer.setProfileImageId(resp.getData().getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return customerRepo.save(customer);
		}				
		return null;
	}
	
	private ImgurResp uploadFile(String file) throws Exception {
		try {
			return imgurClient.uploadImage(file, ImgurClient.ALBUM_ID).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Customer getCustomerById(Long id) {
		return customerRepo.findById(id).orElse(null);
	}
	
	private String createNewStripeCustomer(String email) {
		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
				.setApiKey(secretKey)
				.build();

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("email", email);
			params.put("description", "LIT customer for " + email);		
			com.stripe.model.Customer cust = com.stripe.model.Customer.create(params, reqopt);
			return cust.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
