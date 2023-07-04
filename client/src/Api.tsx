const server = new URL(import.meta.env.VITE_SERVER_URL);
export function getQuotes() {
    return fetch(new URL("quotes", server))
        .then(response => response.json());
}