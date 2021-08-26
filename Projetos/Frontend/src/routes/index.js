import { Redirect, Route, Switch } from "react-router-dom";
import { Home } from "../pages";
import { PrivateRoute } from "./utils";

export const Routes = () => {
  return (
    <Switch>
      <Route exact path='/'>
        <Redirect to='/inicio' />
      </Route>
      <PrivateRoute path='/inicio' component={Home} />
    </Switch>
  )
}