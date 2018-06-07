package co.remotectrl.eventplayer

import org.amshove.kluent.shouldEqual

class AssertUtil<T : Aggregate<T>>{
    fun assertEvent(actual: PlayEvent<T>, expected: PlayEvent<T>) {
        actual.id.value shouldEqual expected.id.value
        actual.aggregateId.value shouldEqual expected.aggregateId.value
        actual.version shouldEqual expected.version
    }

    fun assertAggregateEvent(model: T, evt: PlayEvent<T>) {
        model.latestVersion shouldEqual evt.version
    }
}