package co.remotectrl.eventplayer

interface PlayEvent<TModel : Aggregate<TModel>> {

    val legend: EventLegend<TModel>

    fun applyChangesTo(model: TModel, latestVersion: Int): TModel

    fun applyTo(mutable: MutableAggregate<TModel>) {
        mutable.model = applyChangesTo(mutable.model, legend.version)
    }
}

class MutableAggregate<TModel: Aggregate<TModel>>(var model: TModel)

class EventId<TModel>(val value: Int) where TModel : Aggregate<TModel>{
    constructor() : this(0)
}

class EventLegend<TModel: Aggregate<TModel>>(val eventId: EventId<TModel>, val aggregateId: AggregateId<TModel>, val version: Int){
    constructor(evtIdVal: Int, aggregateIdVal: Int, version: Int) : this(EventId(evtIdVal), AggregateId(aggregateIdVal), version)
    constructor() : this(0, 0, 0)
}