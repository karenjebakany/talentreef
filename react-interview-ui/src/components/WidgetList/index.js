import React, { useEffect, useState } from 'react'
import Grid from '@mui/material/Grid'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'

import WidgetDisplay from '../WidgetDisplay'
import { fetchAllWidgets, deleteWidget, updateWidget, createWidget } from '../../lib/apiConnect'
import Button from '@mui/material/Button'
import { Dialog, DialogActions, DialogContent, DialogTitle, TextField } from '@mui/material'
import { InputAdornment } from '@mui/material'
import SearchIcon from '@mui/icons-material/Search'

const WidgetList = () => {
  const [widgets, setWidgets] = useState([])
  const [open, setOpen] = useState(false)
  const [newWidget, setNewWidget] = useState({ name: '', price: '', description: '' })
  const [searchTerm, setSearchTerm] = useState('')
  const [filteredWidgets, setFilteredWidgets] = useState([])
  useEffect(() => {
    fetchAllWidgets()
      .then(setWidgets)
      .catch((error) => console.error('Error fetching widgets', error))
  }, [])

  const handleOpen = () => setOpen(true)
  const handleClose = () => setOpen(false)

  const handleChange = (e) => {
    setNewWidget({ ...newWidget, [e.target.name]: e.target.value })
  }

  const handleSearch = (e) => {
    setSearchTerm(e.target.value)
    const filtered = widgets.filter((widget) =>
      widget.name.toLowerCase().includes(e.target.value.toLowerCase())
    )
    setFilteredWidgets(filtered)
  }

  useEffect(() => {
    if (searchTerm) {
      const filtered = widgets.filter((widget) =>
        widget.name.toLowerCase().includes(searchTerm.toLowerCase())
      )
      setFilteredWidgets(filtered)
    } else {
      setFilteredWidgets(widgets)
    }

  }, [searchTerm, widgets])

  const handleCreate = async () => {
    try {
      const created = await createWidget(newWidget)
      setWidgets((prev) => [...prev, created])
      // clear the form
      setNewWidget({ name: '', price: '', description: '' })
      handleClose()
    }
    catch (error) {
      if (error.response && (error.response.status === 400 || error.response.status === 404)) {
        console.log(error.response.data)
        alert(error.response.data)
        setNewWidget({ name: '', price: '', description: '' })

      } else {
        alert('Error creating widget')
        setNewWidget({ name: '', price: '', description: '' })
      }
    }
  }

  const handleDelete = async (name) => {
    try {
      await deleteWidget(name)
      console.log('Widget deleted successfully')
      // Update state to re-render
      setWidgets((prev) => prev.filter((widget) => widget.name !== name))
    } catch (error) {
      // if status code  is 400
      if (error.response && error.response.status === 400) {
        alert('Widget not found or already deleted')
      } else {
        alert('Error deleting widget:', error)
      }

    }
  }

  const handleUpdate = async (name) => {
    try {
      await updateWidget(name.name, name)
      console.log('Widget updated successfully')
      // Update state to re-render
      setWidgets((prev) => prev.map((widget) => (widget.name === name.name ? { ...widget, ...name } : widget)))
    } catch (error) {
      if (error.response && (error.response.status === 400 || error.response.status === 404)) {
        console.log(error.response.data)
        if (error.response.data[0] === 'Widget not found with name:', name.name) {
          alert('Cannot change the name of the widget')
        }
        alert(error.response.data)

      } else {
        alert('Error updating widget')
      }
      console.error('Error updating widget')
    }
  }

  return (
    <Stack spacing={4} sx={{ margin: 'auto', maxWidth: 900, paddingTop: '4em', width: '100%' }}>
      <Typography sx={{ textAlign: 'center' }} variant="h3">
        List of Widgets
      </Typography>
      <Stack spacing={2} alignItems="center" marginBottom={3}>
        {/* Search Input */}
        <TextField
          placeholder="Search by name"
          value={searchTerm}
          onChange={handleSearch}
          variant="outlined"
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <SearchIcon />
              </InputAdornment>
            )
          }}

        />

        <Button variant="outlined" onClick={handleOpen}>
          Add New Widget
        </Button>
      </Stack>

      {filteredWidgets.length > 0 ? (
        <Grid container justifyContent="center" spacing={4} sx={{ paddingRight: 4, width: '100%' }}>
          {filteredWidgets.map((current, index) => (

            <WidgetDisplay key={index} widget={current} onDelete={handleDelete} onUpdate={handleUpdate}
            />

          ))}
        </Grid>
      ) : (
        <Grid container justifyContent="center" spacing={4} sx={{ paddingRight: 4, width: '100%' }}>

          {widgets.map((current, index) => (

            <WidgetDisplay key={index} widget={current} onDelete={handleDelete} onUpdate={handleUpdate}
            />

          ))}

        </Grid>
      )}

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Add New Widget</DialogTitle>
        <DialogContent>
          <Stack spacing={2} mt={1}>
            <TextField label="Name" name="name" fullWidth value={newWidget.name} onChange={handleChange} helperText="Name must be between 3 and 100 characters in length and unique" />
            <TextField label="Price" name="price" type="number" fullWidth value={newWidget.price} onChange={handleChange} helperText="Price must be between 5 and 1000 characters" />
            <TextField label="Description" name="description" fullWidth value={newWidget.description} onChange={handleChange} helperText="Description must be a number between 1 and 20,000 with 2 decimal place precision" />
          </Stack>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleCreate} variant="contained">Add</Button>
        </DialogActions>
      </Dialog>

    </Stack>



  )
}

export default WidgetList
