import { useEffect, useState } from "react";
import { getVotes } from "../../api";
import { formatDateTime } from "../../formatters";
import { VoteAndQuoteText } from "../../types";

export default function Votes() {
    const [votes, setVotes] = useState<VoteAndQuoteText[]>([]);

    useEffect(() => {
        getVotes().then((votes) => setVotes(votes));
    }, []);

    return (
        <main>
            <h1>Votes</h1>
            <figure>
                <table>
                    <thead>
                        <tr>
                            <th>Quote</th>
                            <th>Human or AI</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {votes.map((vote, index) => (
                            <tr key={index}>
                                <td>{vote.text}</td>
                                <td>{vote.isReal ? <span className="human">Human</span> : <span className="ai">Ai</span>}</td>
                                <td>{formatDateTime(vote.created)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </figure>
        </main>
    );
}
