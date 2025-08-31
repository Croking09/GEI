import { Link } from 'react-router-dom';
import { FormattedMessage } from 'react-intl';

const Products = ({ products }) => {
    return (
        <table className="table">
            <thead>
            <tr>
                <th>
                    <FormattedMessage id="project.product.columns.name" defaultMessage="Producto"/>
                </th>
                <th>
                    <FormattedMessage id="project.product.columns.price" defaultMessage="Precio Actual"/>
                </th>
                <th>
                    <FormattedMessage id="project.product.columns.remaining" defaultMessage="Minutos Restantes"/>
                </th>
                <th>
                    <FormattedMessage id="project.product.columns.winningBid" defaultMessage="Ganando/Ganador"/>
                </th>
            </tr>
            </thead>
            <tbody>
                {products.map((p, idx) => (
                    <tr key={idx}>
                        <td>
                            <Link
                                to={p.enlace.replace(
                                    '/catalog/productos/',
                                    '/catalog/product-details/'
                                )}
                            >
                                {p.nombre}
                            </Link>
                        </td>
                        <td>{p.precioActual.toFixed(2)}</td>
                        <td>
                            {p.minutosRestantesPuja > 0
                                ? p.minutosRestantesPuja
                                : <FormattedMessage id="project.product.status.finished" defaultMessage="Finalizada"/>
                            }
                        </td>
                        <td>
                            {p.correoGanador
                                ? p.correoGanador
                                : <FormattedMessage id="project.product.status.noBids" defaultMessage="No se realizaron pujas todavía"/>
                            }
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default Products;
