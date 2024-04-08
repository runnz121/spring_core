package org.example.sample.batch.chunk.processor;

import org.example.sample.batch.domain.Product;
import org.example.sample.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {
    @Override
    public Product process(ProductVO item) throws Exception {
        return null;
    }
}
