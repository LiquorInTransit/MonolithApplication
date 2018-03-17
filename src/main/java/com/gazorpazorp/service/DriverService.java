package com.gazorpazorp.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gazorpazorp.client.ImgurClient;
import com.gazorpazorp.model.Driver;
import com.gazorpazorp.model.dto.DriverInfoUpdateDto;
import com.gazorpazorp.model.imgur.ImgurResp;
import com.gazorpazorp.repository.DriverRepository;
import com.stripe.model.Account;
import com.stripe.net.RequestOptions;

@Service
public class DriverService {

	@Autowired
	DriverRepository driverRepo;
	@Autowired
	ImgurClient imgurClient;
	
	@Value("${stripe.secret-key}")
	String secretKey;
	
	private final Logger logger = LoggerFactory.getLogger(DriverService.class);
	
	//use the userClietn to get the user, adn put everything to gether into a CustomerMeDto
	public Driver getCurrentDriver () {
		Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
		return driverRepo.findByUserId(id);
	}
	public Driver getDriverById(Long id) {
		return driverRepo.findById(id).orElse(null);
	}
	
	public Driver updateProfilePic(byte[] profilePic) {
		Driver driver = getCurrentDriver();
		if (driver != null) {
			//customer.setProfilePic(profilePic);
			return driverRepo.save(driver);
		}		
		return null;
	}
	
	public Driver updateDriver(DriverInfoUpdateDto dto) {
		Driver driver = getCurrentDriver();
		if (driver != null && dto != null) {
			dto.Incorporate(driver);
			try {
				ImgurResp resp = uploadFile(dto.getFile());
				if (resp != null) {
					driver.setProfileImageId(resp.getData().getId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return driverRepo.save(driver);
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
	
	public Driver createDriver(Driver driver, String fName, String lName, HttpServletRequest req) {
		String stripeId = createNewStripeAccount(fName, lName, req);
		if (stripeId == null)
			return null;
		driver.setStripeId(stripeId);
		return driverRepo.save(driver);
	}
	
	public void deleteDriverByUserId(Long userId) {
		driverRepo.deleteByUserId(userId);
	}
	
	private String createNewStripeAccount(String fName, String lName, HttpServletRequest req) {
		RequestOptions reqopt = (new RequestOptions.RequestOptionsBuilder())
				.setApiKey(secretKey)
				.build();

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("type", "custom");
			params.put("country", "CA");
			params.put("legal_entity[type]", "individual");
			params.put("legal_entity[first_name]", fName);
			params.put("legal_entity[last_name]", lName);
			params.put("legal_entity[address][city]", "Oakville");
			params.put("legal_entity[dob][day]", 6);
			params.put("legal_entity[dob][month]", 6);
			params.put("legal_entity[dob][year]", 1966);
			Integer now = (int) (System.currentTimeMillis()/1000L);
			params.put("tos_acceptance[date]", now);
			params.put("tos_acceptance[ip]", req.getRemoteAddr());			
			Account account = Account.create(params, reqopt);
			return account.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
