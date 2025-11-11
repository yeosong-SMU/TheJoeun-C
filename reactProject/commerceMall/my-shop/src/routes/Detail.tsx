import { useParams } from "react-router-dom";

export default function Detail(props:any) {
    console.log("props",props);
    let { id } = useParams();
    console.log("id",id);

    let dataId = props.shoes.find((x:any) => x.id == id);
    console.log("dataId",dataId);

    return (
        <div className="container">
        <div className="row">
            <div className="col-md-6">
            <img
                src={"https://raw.githubusercontent.com/lshjju/cdn/refs/heads/main/ca-shop/s"+(dataId.id + 1)+".PNG"}   //id
                width="100%"
            />
            </div>
            <div className="col-md-6">
            <h4 className="pt-5">{props.shoes[0].title}</h4>
            <p>{props.shoes[0].content}</p>
            <p>{props.shoes[0].price}</p>
            <button className="btn btn-danger">order</button>
            </div>
        </div>
        </div>
    );
}