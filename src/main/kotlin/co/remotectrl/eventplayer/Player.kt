package co.remotectrl.eventplayer

class Player<TAggregate: Aggregate<TAggregate>> {
    fun playFor(evts: Array<PlayEvent<TAggregate>>, aggregate: TAggregate) {
        for(evt in evts){
            evt.applyTo(MutableAggregate(aggregate = aggregate))
        }
    }

}