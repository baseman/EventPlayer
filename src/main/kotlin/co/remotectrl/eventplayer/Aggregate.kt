package co.remotectrl.eventplayer

abstract class Aggregate<TModel : Aggregate<TModel>> {
    abstract val legend: AggregateLegend<TModel>
}

class AggregateId<TModel>(val value: Int) where TModel : Aggregate<TModel>{
    constructor() : this(0)
}

class AggregateLegend<TModel: Aggregate<TModel>>(val aggregateId: AggregateId<TModel>, val latestVersion: Int){
    constructor(aggregateIdVal: Int, latestVersion: Int) : this(AggregateId(aggregateIdVal), latestVersion)
    constructor() : this(0, 0)
}