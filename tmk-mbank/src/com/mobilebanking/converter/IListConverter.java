/**
 * 
 */
package com.mobilebanking.converter;

import java.util.List;

/**
 * @author bibek
 *
 */
public interface IListConverter<E, D> {
	
	public List<D> convertToDtoList(List<E> entityList);
}
