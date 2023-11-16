import { useEffect, useState } from "react";
import { getVotes } from "../../api";
import { formatDateTime } from "../../formatters";
import { VoteAndQuoteText } from "../../types";
import { useStore } from "../../store";
import { shallow } from "zustand/shallow";
import { Link } from "react-router-dom";

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
            {votes.length === 0 ? 
                <p>You have not voted on any quote. <Link to="/vote">Start voting now!</Link></p>
                :
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
                                {vote.isReal ? (
                                    <td className="human center">Human</td>
                                ) : (
                                    <td className="ai center">Ai</td>
                                )}
                                <td className="middle">
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
        }    
        </main>
    );
}
