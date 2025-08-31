import { Link } from 'react-router-dom';
import { FormattedDate, FormattedTime, FormattedMessage } from 'react-intl';

const Bids = ({ bids }) => {
    return (
        <table className="table">
            <thead>
            <tr>
                <th>
                    <FormattedMessage
                        id="project.bid.columns.product"
                        defaultMessage="Producto"
                    />
                </th>
                <th>
                    <FormattedMessage
                        id="project.bid.columns.amount"
                        defaultMessage="Importe"
                    />
                </th>
                <th>
                    <FormattedMessage
                        id="project.bid.columns.date"
                        defaultMessage="Fecha"
                    />
                </th>
                <th>
                    <FormattedMessage
                        id="project.bid.columns.status"
                        defaultMessage="Estado"
                    />
                </th>
            </tr>
            </thead>
            <tbody>
            {bids.map((b, idx) => {
                const date = b.fecha ? new Date(b.fecha) : null;
                return (
                    <tr key={idx}>
                        <td>
                            <Link
                                to={b.enlace.replace(
                                    '/catalog/productos/',
                                    '/catalog/product-details/'
                                )}
                            >
                                {b.nombreProducto}
                            </Link>

                        </td>
                        <td>{b.maxPujado.toFixed(2)}</td>
                        <td>
                            {date ? (
                                <>
                                    <FormattedDate
                                        value={date}
                                        year="numeric" month="2-digit" day="2-digit"
                                    />{' '}
                                    <FormattedTime
                                        value={date}
                                        hour="2-digit" minute="2-digit"
                                    />
                                </>
                            ) : (
                                '—'
                            )}
                        </td>
                        <td>
                            <FormattedMessage
                                id={`project.bid.status.${b.estado.toLowerCase()}`}
                                defaultMessage={b.estado}
                            />
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </table>
    );
};

export default Bids;
