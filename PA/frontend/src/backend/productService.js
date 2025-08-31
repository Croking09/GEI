import {appFetch} from './appFetch';

export const findUserProducts = async ({ page = 0 } = {}) =>
    await appFetch('GET', `/productos/user?page=${page}`);

export const insertProduct = async (nombre, descripcion, duracionPuja, precioSalida, informacionEnvio, categoriaId) =>
    await appFetch('POST', `/productos`,
        {nombre, descripcion, duracionPuja, precioSalida, informacionEnvio, categoriaId});