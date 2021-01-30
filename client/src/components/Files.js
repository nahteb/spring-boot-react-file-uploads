import React, { Component } from 'react';
import SingleFile from './SingleFile';
import AddFiles from './AddFiles';

export default class Files extends Component {
  constructor(props) {
    super(props);
    this.state = {
      files: [],
    };
  }

  componentDidMount() {
    fetch('http://localhost:8080/getFiles/')
    .then(response => response.json())
    .then(data => this.setState({files: data}))
  }

  render() {
    return (
        <div>
          <div className="row">
            <AddFiles />
          </div>
          <div><p>Uploaded Files: </p></div>
          <div className="row">
            <ul>
              { this.state.files.map((item) => (
                <SingleFile key={item.id} item={item} />
              ))}
            </ul>
          </div>
        </div>
    )
  }

}
