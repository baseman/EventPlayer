package co.remotectrl.eventplayer

interface PlayEvent<TAggregate : Aggregate<TAggregate>> {

    val legend: EventLegend<TAggregate>

    fun applyChangesTo(aggregate: TAggregate, latestVersion: Int): TAggregate

    fun applyTo(mutable: MutableAggregate<TAggregate>) {
        mutable.aggregate = applyChangesTo(mutable.aggregate, legend.version)
    }
}

class MutableAggregate<TAggregate: Aggregate<TAggregate>>(var aggregate: TAggregate)

class EventId<TAggregate>(val value: String) where TAggregate : Aggregate<TAggregate>

class EventLegend<TAggregate: Aggregate<TAggregate>>(val eventId: EventId<TAggregate>, val aggregateId: AggregateId<TAggregate>, val version: Int){
    constructor(evtIdVal: String, aggregateIdVal: String, version: Int) : this(EventId(evtIdVal), AggregateId(aggregateIdVal), version)
}