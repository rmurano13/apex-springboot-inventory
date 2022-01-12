package com.apex.eqp.inventory.services;

import com.apex.eqp.inventory.entities.Product;
import com.apex.eqp.inventory.entities.RecalledProduct;
import com.apex.eqp.inventory.helpers.ProductFilter;
import com.apex.eqp.inventory.repositories.InventoryRepository;
import com.apex.eqp.inventory.repositories.RecalledProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final InventoryRepository inventoryRepository;
    private final RecalledProductRepository recalledProductRepository;

    public Product save(Product product) {
        return inventoryRepository.save(product);
    }
    
    public Collection<Product> getAllProduct(){
    	
    	//Query for all products
    	List<Product> products = inventoryRepository.findAll();
    	
    	HashSet<String> recalledProductSet = new HashSet<>();
    	
    	//Query for all Recalled Products
    	List<RecalledProduct> recalledProducts = recalledProductRepository.findAll();
    	
    	//Add all recalled prodcut names to set
    	if(recalledProducts!=null) {    	
    		for(RecalledProduct recalled: recalledProducts) {
    			recalledProductSet.add(recalled.getName());
    		}
    	}
    	
    	List<Product> nonRecalledProducts = new ArrayList<>();
    	
    	
    	if(products!=null) {
    		for(Product product: products) {
    			if(!recalledProductSet.contains(product.getName())) {
    				nonRecalledProducts.add(product);
    			}
    		}
    	}
    	
    	return nonRecalledProducts;
    	}


    public Optional<Product> findById(Integer id) {
        return inventoryRepository.findById(id);
    }
}
