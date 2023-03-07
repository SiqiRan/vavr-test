package io.vavr.experiment;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.Setter;
import io.vavr.experiment.entity.Parent;
import io.vavr.experiment.entity.Mother;

@Getter
@Setter
public class ListPropertiesExperiment {
    private List<Parent> vavrList = List.of(new Mother());
}
