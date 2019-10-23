<!DOCTYPE html>
<!-- [START_EXCLUDE] -->
<%--
  ~ Copyright 2017 Google Inc.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License"); you
  ~ may not use this file except in compliance with the License. You may
  ~ obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
  ~ implied. See the License for the specific language governing
  ~ permissions and limitations under the License.
  --%>
<!-- [END_EXCLUDE] -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
  <title>HangManWebGame</title>
</head>
<body>

  <h1>HangMan</h1>


  <form method="GET" action="/game">

    <div class="form-group">
      <label for="name">Player Name</label>
      <input type="text" name="name" id="name" value="" class="form-control" />
    </div>
    <button type="submit" class="btn btn-success">New Game</button>
  </form>
</body>
</html>