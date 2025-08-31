import {appFetch} from './appFetch';

export const bid = async (valor, productoId) =>
    await appFetch('POST', `/pujas`, {valor, productoId});

export const findUserBids = async ({ page = 0 } = {}) =>
    await appFetch('GET', `/pujas/user?page=${page}`);