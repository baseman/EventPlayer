package co.remotectrl.eventplayer

interface Aggregate<TAggregate : Aggregate<TAggregate>> {
    val legend: AggregateLegend<TAggregate>
}

class AggregateId<TAggregate>(val value: String) where TAggregate : Aggregate<TAggregate>

class AggregateLegend<TAggregate: Aggregate<TAggregate>>(val aggregateId: AggregateId<TAggregate>, val latestVersion: Int){
    constructor(aggregateIdVal: String, latestVersion: Int) : this(AggregateId(value = aggregateIdVal), latestVersion = latestVersion)
}