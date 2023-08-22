import { Outlet } from "react-router";
import Navigation from "./Navigation";
import { useStore } from "../store";
import Oops from "../pages/Oops";

export default function Layout() {

    const exception = useStore((state) => state.exception);
    
    if (exception !== null) {
        return <Oops exception={exception}></Oops>
    }

    return <>
        <Navigation />
        <Outlet />
    </>
}