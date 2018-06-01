package co.remotectrl.eventplayer

abstract class Aggregate<TModel : Aggregate<TModel>>(var latestVersion : Int = 0){
    abstract val id: AggregateId<TModel>
}

class AggregateId<TModel>(val value: Int) where TModel : Aggregate<TModel>
