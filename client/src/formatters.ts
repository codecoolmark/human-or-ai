export function formatDateTime(instant: string): string {
    return new Intl.DateTimeFormat(navigator.language, {
        dateStyle: "full", timeStyle: "medium"
    }).format(new Date(instant));
}
