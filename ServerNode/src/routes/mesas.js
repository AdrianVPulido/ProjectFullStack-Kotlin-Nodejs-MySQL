module.exports = app => {

    const Mesas = app.db.models.mesas;

    app.route('/mesas')
    .get((req, res) => {
        Mesas.findAll({})
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .post((req, res) => {
        Mesas.create(req.body)
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    });

    app.route('/mesas/:id')
    .get((req, res) => {
        Mesas.findOne({where: req.params})
        .then(result => res.json(result))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
    .put((req, res) => {
        Mesas.update(req.body,{where: req.params})
        .then (result => res.sendStatus(204))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })

    .delete((req, res) => {
        Mesas.destroy({where: req.params})
        .then (result => res.sendStatus(204))
        .catch(error => {
            res.status(412).json({msg: error.message});
        });
    })
};