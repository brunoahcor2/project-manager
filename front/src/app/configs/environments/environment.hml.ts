export const environment = {
    production: false,
    api: {
      url: 'http://localhost:8080/project-manager/api/v1',
      person: {
        domain: '/persons',
        endpointRegistry: '/registry'
      },
      project: {
        domain: '/projects',
        endpointRegistry: '/registry'
      }
    }
 };
