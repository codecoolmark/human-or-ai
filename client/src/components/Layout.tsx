import { Outlet } from "react-router";
import Navigation from "./Navigation";

export default function Layout() {
    return <>
        <Navigation />
        <Outlet />
    </>
}