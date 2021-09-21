/**
 * 
 */
package com.mobilebanking.converter;

/**
 * @author bibek
 *
 */
public interface IConverter <E,D>{
	
	public E convertToEntity(D dto);
	
	public D convertToDto(E entity);
}
