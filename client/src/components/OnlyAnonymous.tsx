import { useStore } from "../store";

export default function OnlyAnonymous({ children }: { children: JSX.Element | JSX.Element[] }) {
    const user = useStore((state) => state.user);

    if (user === null) {
        return <>{children}</>
    }

    return <></>

}