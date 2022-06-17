package cl.stgoneira.ejemplodaggerhilt.data.repo

import cl.stgoneira.ejemplodaggerhilt.data.dao.ProductoDao
import cl.stgoneira.ejemplodaggerhilt.data.entity.ProductoEntity
import javax.inject.Inject

class ProductoRepository @Inject constructor(private val productoDao: ProductoDao) {

    fun findAll():List<ProductoEntity> = productoDao.getAll()

    fun crear(producto:ProductoEntity) = productoDao.insertAll(producto)

    fun borrarTodo() = productoDao.deleteAll()
}