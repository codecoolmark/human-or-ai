import { useEffect, useState } from "react"
import { getVotes } from "../../api";
import { formatDateTime } from "../../formatters";

export default function Votes() {
    const [votes, setVotes] = useState([]);

    useEffect(() => {
        getVotes().then(votes => setVotes(votes))
    }, []);


    return <main>
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
                    {votes.map((vote, index) => <tr key="index">
                        <td>{vote.quoteId}</td>
                        <td>{vote.real ? "Human" : "AI"}</td>
                        <td>{formatDateTime(vote.created)}</td>
                    </tr>)}
                </tbody>
            </table>
        </figure>
    </main>
}