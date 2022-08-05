package code.spring.recipe.services;

import code.spring.recipe.commands.UnitOfMeasureCommand;


import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAll();
}
