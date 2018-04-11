abstract class Aggregate<TModel : Aggregate<TModel>>(var latestVersion : Int = 0){
    abstract val id: AggregateId<TModel>
}

class AggregateId<TModel>(val Value: Int) where TModel : Aggregate<TModel>
