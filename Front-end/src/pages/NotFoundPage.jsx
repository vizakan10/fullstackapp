import { Link } from "react-router-dom";

export default function NotFoundPage() {
  return (
    <div>
      <p>404 NOT Found</p>
      <Link to="/">Home from link</Link>
    </div>
  );
}
