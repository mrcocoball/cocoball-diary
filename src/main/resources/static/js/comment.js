async function getList({aid, page, size, goLast}){

    const result = await axios.get(`/comments/list/${aid}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({aid:aid, page:lastPage, size:size})

    }

    return result.data
}

async function addComment(commentObj) {

    const response = await axios.post(`/comments/`,commentObj)
    return response.data
}

async function getComment(cid) {

    const response = await axios.get(`/comments/${cid}`)
    return response.data
}

async function modifyComment(commentObj) {

    const response = await axios.put(`/comments/${commentObj.cid}`, commentObj)
    return response.data
}

async function removeReply(cid) {

    const response = await axios.delete(`/comments/${cid}`)
    return response.data
}