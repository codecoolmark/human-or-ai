import { useEffect, useState } from "react";
import { getVotes } from "../../api";
import { formatDateTime } from "../../formatters";
import { VoteAndQuoteText } from "../../types";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";

export default function Votes() {
    const [votes, setVotes] = useState<VoteAndQuoteText[]>([]);
    const [setException] = useStore(
        (state) => [state.setException],
        shallow,
    );

    useEffect(() => {
        getVotes().then((votes) => setVotes(votes)).catch(setException);
    }, [setException]);

    return (
        <main>
            <h1>Votes</h1>
            <figure>
                <table>
                    <thead>
                        <tr>
                            <th>Quote</th>
                            <th>Your vote</th>
                            <th>Are you correct?</th>
                            <th>Date voted</th>
                        </tr>
                    </thead>
                    <tbody>
                        {votes.map((vote, index) => (
                            <tr key={index}>
                                <td>{vote.text}</td>
                                <td>
                                    {vote.isReal ? (
                                        <span className="human">Human</span>
                                    ) : (
                                        <span className="ai">Ai</span>
                                    )}
                                </td>
                                <td>
                                    {vote.isCorrect === null ? (
                                        <span>To be announced...</span>
                                    ) : vote.isCorrect ? (
                                        <span>Correct!</span>
                                    ) : (
                                        <span>Not yet correct</span>
                                    )}
                                </td>
                                <td>{formatDateTime(vote.created)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </figure>
        </main>
    );
}
