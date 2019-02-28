package co.remotectrl.eventplayer

interface Aggregate<TAggregate : Aggregate<TAggregate>> {
    val legend: AggregateLegend<TAggregate>
}

class AggregateId<TAggregate>(val value: Int) where TAggregate : Aggregate<TAggregate>{
    constructor() : this(value = 0)
}

class AggregateLegend<TAggregate: Aggregate<TAggregate>>(val aggregateId: AggregateId<TAggregate>, val latestVersion: Int){
    constructor(aggregateIdVal: Int, latestVersion: Int) : this(AggregateId(value = aggregateIdVal), latestVersion = latestVersion)
    constructor() : this(aggregateIdVal = 0, latestVersion = 0)
}