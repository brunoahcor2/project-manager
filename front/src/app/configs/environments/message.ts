export const message = {
  success: {
    person: {
      registeredSuccess: 'Person registered successfully!',
      updateSuccess: 'Person updated successfully!',
      deletedSuccess: 'Person successfully deleted',
    },
    project: {
      registeredSuccess: 'Project registered successfully!',
      updateSuccess: 'Project updated successfully!',
      deletedSuccess: 'Project successfully deleted',
    }
  },
  errors: {
    person: {
      notRegistered: 'Could not register your person.',
      notChanged: 'Person cannot be changed.',
      notFound: 'No person registered.',
      notFoundById: 'The person with the given id {id} could not be found.'
    },
    project: {
      notRegistered: 'Could not register your project.',
      notChanged: 'Project cannot be changed.',
      notFound: 'No project registered.',
      notFoundById: 'The project with the given id {id} could not be found.'
    }
  }
};
