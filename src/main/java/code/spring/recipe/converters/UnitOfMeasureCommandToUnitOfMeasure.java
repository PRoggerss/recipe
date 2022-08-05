package code.spring.recipe.converters;

import code.spring.recipe.commands.UnitOfMeasureCommand;
import code.spring.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if(source == null){
            final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
            unitOfMeasure.setId(source.getId());
            unitOfMeasure.setDescription(source.getDescription());

            return unitOfMeasure;
        }
        return null;
    }
}
