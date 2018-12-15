module.exports = app => {

    const Comandas = app.db.models.comandas;

    app.route('/comandas')
    .get((req, res) => {
        Comandas.findAll({})
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .post((req, res) => {
        Comandas.create(req.body)
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    });

    app.route('/comandas/:id')
    .get((req, res) => {
        Comandas.findOne({where: req.params})
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .put((req, res) => {
        Comandas.update(req.body,{where: req.params})
        .then (result => res.sendStatus(204))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })

    .delete((req, res) => {
        Comandas.destroy({where: req.params})
        .then (result => res.sendStatus(204))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
};