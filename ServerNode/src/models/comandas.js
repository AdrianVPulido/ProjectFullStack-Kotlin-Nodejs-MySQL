module.exports = (sequelize, DataType) => {

    const Comandas = sequelize.define('comandas', {

        idcomanda: {
            type: DataType.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            allowNull: false
        },
        mesaid: {
            type: DataType.INTEGER,
            foreignKey: true,
            allowNull: false
        },
        estado: {
            type: DataType.STRING,
            allowNull: false,
            validate: {
                notEmpty: true
            }
        }
    }, {
        underscored : true,
        timestamps : false,
        paranoid: false
    });


    Comandas.associate = (models) => {
        Comandas.belongsTo(models.mesas, {foreignKey: 'mesaid', targetKey: 'idmesa'});
        Comandas.belongsToMany(models.productos, {through: 'contienes', foreignKey:'idcomanda'});
    };

    return Comandas
};