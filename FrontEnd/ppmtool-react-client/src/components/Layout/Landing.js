import React, { Component } from "react";

class Landing extends Component {
  render() {
    return (
      <div classname="landing">
        <div classname="light-overlay landing-inner text-dark">
          <div classname="container">
            <div classname="row">
              <div classname="col-md-12 text-center">
                <h1 classname="display-3 mb-4">
                  Personal Project Management Tool
                </h1>
                <p classname="lead">
                  Create your account to join active projects or start your own
                </p>
                <hr />
                <a href="register.html" classname="btn btn-lg btn-primary mr-2">
                  Sign Up
                </a>
                <a href="login.html" classname="btn btn-lg btn-secondary mr-2">
                  Login
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Landing;
