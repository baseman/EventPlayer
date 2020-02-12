package co.remotectrl.eventplayer

interface PlayCommand<TAggregate : Aggregate<TAggregate>> {

    fun getEventLegend(aggregateId: AggregateId<TAggregate>, version: Int) : EventLegend<TAggregate>{
        return EventLegend(
                0.toString(),
                aggregateId.value,
                version
        )
    }

    fun getEvent(eventLegend: EventLegend<TAggregate>): PlayEvent<TAggregate>

    fun validate(aggregate: TAggregate, validation: PlayValidation)

    fun executeOn(aggregate: TAggregate): PlayExecution<TAggregate, PlayEvent<TAggregate>, PlayInvalidation<TAggregate>> {
        val validation = PlayValidation(mutableListOf())

        validate(aggregate, validation)

        val validatedItems = validation.invalidInputItems.toTypedArray()

        return if (validatedItems.isNotEmpty()) PlayExecution.Invalidated(items = validatedItems)
        else {
            PlayExecution.Validated(
                    event = getEvent(
                            getEventLegend(aggregateId = aggregate.legend.aggregateId, version = aggregate.legend.latestVersion + 1)
                    )
            )
        }
    }
}

sealed class PlayExecution<TAggregate : Aggregate<TAggregate>, out TEvent : PlayEvent<TAggregate>, out TInvalid: PlayInvalidation<TAggregate>>{
    class Validated<TAggregate : Aggregate<TAggregate>, out TEvent : PlayEvent<TAggregate>>(
            val event: PlayEvent<TAggregate>
    ) : PlayExecution<TAggregate, TEvent, Nothing>()

    class Invalidated<TAggregate : Aggregate<TAggregate>>(
            val items: Array<PlayInvalidInput>
    ) : PlayExecution<TAggregate, Nothing, PlayInvalidation<TAggregate>>()
}

class PlayInvalidation<TAggregate>(items: Array<PlayInvalidInput>)

class PlayValidation(internal val invalidInputItems: MutableList<PlayInvalidInput>){
    fun assert(that: () -> Boolean, description: String){

        when {
            !that() -> invalidInputItems.add(PlayInvalidInput(description = description))
        }
    }
}

class PlayInvalidInput(val description: String)