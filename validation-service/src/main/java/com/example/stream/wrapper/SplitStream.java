package com.example.stream.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Predicate;

import java.util.UUID;

/**
 * Represents kafka stream divided into two sub-streams by some condition
 *
 * @param <K> key type
 * @param <V> value type
 */
@RequiredArgsConstructor
@Getter
public class SplitStream<K, V> {

    private static final String MATCHED_STREAM_SUFFIX = "-matched";
    private static final String NON_MATCHED_STREAM_SUFFIX = "-non-matched";

    private final KStream<K, V> matchedStream;
    private final KStream<K, V> nonMatchedStream;

    /**
     * Splits given {@code streamToSplit} into two separate sub-streams using given {@code predicate}
     *
     * @param streamToSplit stream to split
     * @param predicate     splitting condition
     * @return {@link SplitStream}
     */
    public static <K, V> SplitStream<K, V> split(KStream<K, V> streamToSplit, Predicate<K, V> predicate) {
        var prefix = UUID.randomUUID().toString();
        var initialStreamBranches = streamToSplit
            .split(Named.as(prefix))
            .branch(predicate, Branched.as(MATCHED_STREAM_SUFFIX))
            .defaultBranch(Branched.as(NON_MATCHED_STREAM_SUFFIX));

        return new SplitStream<>(
            initialStreamBranches.get(prefix + MATCHED_STREAM_SUFFIX),
            initialStreamBranches.get(prefix + NON_MATCHED_STREAM_SUFFIX)
        );
    }

}