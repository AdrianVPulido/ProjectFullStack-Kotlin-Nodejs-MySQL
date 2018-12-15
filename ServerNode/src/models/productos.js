module.exports = (sequelize, DataType) => {

    const Productos = sequelize.define('productos', {

        idproducto: {
            type: DataType.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },

        categoria: {
            type: DataType.STRING,
            allowNull: false,
            validate: {
                notEmpty : true
            }
        },

        nombre: {
            type: DataType.STRING,
            allowNull: false,
            validate: {
                notEmpty : true
            }
        },

        precio: {
            type: DataType.DOUBLE,
            allowNull: false,
            validate: {
                notEmpty : true
            }
        }
    }, {
        underscored : true,
        timestamps : false,
        paranoid: false
    });

    
    Productos.associate = (models) => {
        Productos.belongsToMany(models.comandas, {through: 'contienes', foreignKey: 'idproducto'});
    };

    return Productos
};