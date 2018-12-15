module.exports = (sequelize, DataType) => {

    const Mesas = sequelize.define('mesas', {

        idmesa: {
            type: DataType.INTEGER,
            primaryKey: true,
            autoIncrement: true
        },
        nombre: {
            type: DataType.STRING,
            allowNull: false,
            validate: {
                notEmpty : true
            }
        }}, {
            underscored : true,
            timestamps : false,
            paranoid: false
        });

    Mesas.associate = (models) => {
        Mesas.hasMany(models.comandas, {foreignKey: 'mesaid', sourceKey:'idmesa'});
    };

    return Mesas
};