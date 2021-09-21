/**
 * 
 */
package com.mobilebanking.entity;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="oauth_code")
public class OAuthCode {

		@Id
		private String code;
		
		private Blob authentication;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Blob getAuthentication() {
			return authentication;
		}

		public void setAuthentication(Blob authentication) {
			this.authentication = authentication;
		}

		

}
