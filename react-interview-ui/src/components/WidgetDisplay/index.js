import React from 'react'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import Grid from '@mui/material/Grid'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import { Button, Dialog, DialogContent } from '@mui/material'
import { DialogActions, DialogTitle, TextField } from '@mui/material'
import { createWidget } from '../../lib/apiConnect'

const DisplayWidget = ({ widget, onDelete, onUpdate }) => {
  const { description, name, price } = widget
  const [open, setOpen] = React.useState(false)
  const [formData, setFormData] = React.useState({
    name: name,
    description: description,
    price: price
  })

  const handleChange = (event) => {
    const { name, value } = event.target
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }))
  }
  const handleClickOpen = () => {
    setFormData({
      name: name,
      description: description,
      price: price
    })
    setOpen(true)
  }
  const handleClose = () => {
    setOpen(false)
  }

  const handleSubmit = async () => {
    await onUpdate(formData)
    setOpen(false)
  }

  const handleDelete = async () => {
    await onDelete(name)
  }




  return (
    <Grid item xs={6}>
      <Card>
        <CardContent>
          <Stack spacing={2}>
            <Typography component="div" gutterBottom variant="h4">
              {name}
            </Typography>
            <Typography component="div" gutterBottom variant="h5">
              ${price}
            </Typography>
            <Typography color="text.secondary" variant="body2">
              {description}
            </Typography>
            <Stack direction="row" spacing={2} justifyContent="flex-end">
              <Button onClick={() => handleDelete()}>Delete</Button>
              <Button onClick={handleClickOpen}>Edit</Button>
            </Stack>
          </Stack>

        </CardContent>
      </Card>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Edit Widget</DialogTitle>
        <DialogContent>
          <Stack spacing={2} mt={1}>
            <TextField
              label="Name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              label="Price"
              name="price"
              type="number"
              value={formData.price}
              onChange={handleChange}
              fullWidth
            />
            <TextField
              label="Description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              fullWidth
            />
          </Stack>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSubmit} variant="contained">Save</Button>
          </DialogActions>
        </DialogContent>
      </Dialog>
    </Grid>


  )
}

export default DisplayWidget
