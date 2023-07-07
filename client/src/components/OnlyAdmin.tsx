import { useStore } from "../store";

export default function OnlyAdmin({ children }: { children: JSX.Element | JSX.Element[] }) {
    const user = useStore((state) => state.user);

    if (user === null || user.isAdmin !== true) {
        return <></>
    }

    return <>{children}</>

}