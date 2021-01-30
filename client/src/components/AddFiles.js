import React, { Component } from 'react';

export default class AddFiles extends Component {

  constructor(props) {
    super(props);
    this.state ={
      file:null
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  onChange(event) {
    this.setState({file:event.target.files[0]})
  }

  handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData();
    formData.append('file', this.state.file);
    fetch('http://localhost:8080/uploadFile', {
      method: 'post',
      body: formData
    }).then(res => {
      if(res.ok) {
        console.log(res.data);
        alert("File uploaded successfully.")
      }
    });
    window.location.reload();
  }

  render() {
    return (
        <div>
          <form onSubmit={this.handleSubmit}>
            <div className="form-group">
              <p>
                File to upload:
              </p>
              <input type="file" onChange={this.onChange} class="form-control-file" id="exampleFormControlFile1" />
            </div>
            <div className="form-group">
              <button type="submit" class="customButton">Upload</button>
            </div>
          </form>
        </div>
    );
  }



}
