import { useState } from 'react'
import './App.css'
import "./css/bootstrap.min.css";
import data from "./data.tsx";
import { Routes, Route, Link, Outlet, useNavigate } from "react-router-dom";
import Detail from "./routes/Detail";
import axios from "axios";

function App() {

  let [shoes] = useState(data);
  const navigate = useNavigate();

  const id = 1;
  return (
     <div className="App">
      
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">Navbar</a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div className="navbar-nav">
              <Link to="/" className="nav-link active" aria-current="page">
                Home
              </Link>
              <Link to={`/detail/${id}`} className="nav-link active" aria-current="page">
                Detail
              </Link>
              <Link
                to="/company/manpower"
                className="nav-link active"
                aria-current="page"
              >
                Manpower
              </Link>
              <Link
                to="/company/map"
                className="nav-link active"
                aria-current="page"
              >
                Map
              </Link>              
            </div>

          </div>
        </div>
      </nav>      

      <Routes>
        <Route
          path="/"
          element={
            <>
              <div className="main-bg"></div>

              <h1 className="my-5">E-commerce project</h1>

              <div className="d-flex flex-column mb-3">
                {shoes.map((shoe, i) => {
                  return <Goods shoes={shoe} i={i} key = {i}></Goods>;
                })}
              </div>
            </>
          }
        />        

        <Route path="/detail/:id" element={<Detail shoes={shoes} />} />
        <Route path="/company/" element={<Company />} >
          <Route path="manpower" element={<Manpower />} />
          <Route path="map" element={<Map />} />
        </Route>
        <Route path="*" element={<Nopage />} />        
      </Routes>

      <div className="card">
        <div className="card-header">
          Featured
        </div>
        <div className="card-body">
          <h5 className="card-title">Special title treatment</h5>
          <p className="card-text">With supporting text below as a natural lead-in to additional content.</p>
          <a href="#" className="btn btn-primary">Go somewhere</a>
        </div>
      </div>

    </div>
  )
}

function Goods(props:any) {
  
  return (
    <div className="p-2">
      <img
        src={"https://raw.githubusercontent.com/visualjd/shop/refs/heads/main/images/s"+(props.shoes.id + 1)+".PNG"}
        width="80%"
      />
      <h4 className="my-3">{props.shoes.title}</h4>
      <p>{props.shoes.price}</p>
    </div>
  );
}

function Company() {
  return (
    <div>
      <h4>company</h4>
      It's a company
      <Outlet></Outlet>
    </div>   
  );
}

function Manpower() {
  return (
    <div>
      <img
        src="https://plus.unsplash.com/premium_photo-1688821131205-52f5c633ce69?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        width="80%"
      />
    </div>
  );
}

function Map() {
  return (
    <div>
      <img
        src="https://images.unsplash.com/photo-1548345680-f5475ea5df84?q=80&w=2073&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
        width="80%"
      />
    </div>
  );
}

function Nopage() {
  return (
    <div>
      <h4 className="my-2">No page</h4>
      <p>hmmm....</p>
      <img
        src="https://cdn.maily.so/ixmvzk5qh83mee5kcjw8pp55fihe"
        width="80%"
      />
    </div>
  );
}

export default App
