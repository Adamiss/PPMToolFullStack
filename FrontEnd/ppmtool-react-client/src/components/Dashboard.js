import React, { Component } from "react";
import Header from "./Layout/Header";
import ProjectItem from "./Project/ProjectItem";

export default class Dashboard extends Component {
  render() {
    return (
      <div>
        <Header />
        <h1>Welcome to the Dashboard</h1>
        <ProjectItem />
      </div>
    );
  }
}
