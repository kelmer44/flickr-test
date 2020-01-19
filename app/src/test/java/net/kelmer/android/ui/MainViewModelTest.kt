package net.kelmer.android.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import java.util.concurrent.Future
import net.kelmer.android.common.Resource
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.domain.PhotoListPage
import net.kelmer.android.network.task.Callback
import net.kelmer.android.network.task.FutureTask
import net.kelmer.android.network.task.TaskRunner
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val TEST_ERROR = "Test error"
    private val TEST_SEARCH = "Test search"
    private val TEST_PAGE = 1

    @Test
    fun `When receiving an exception, we map into a Failure object preceeded by InProgress`() {

        val futureMock = mock<Future<PhotoListPage>> { }

        val deferredTaskRunner: TaskRunner<PhotoListPage> = mock {
            on {
                this.execute(any())
            }
                .thenAnswer {
                    (it.arguments[0] as Callback<*>).onFailure(Throwable(TEST_ERROR))
                    return@thenAnswer FutureTask(futureMock)
                }
        }

        val photoRepository: PhotoRepository = mock {
            on {
                search(any(), any())
            }.doReturn(deferredTaskRunner)
        }

        val observer: Observer<Resource<PhotoListPage>> = mock { }

        val mainViewModel = MainViewModel(photoRepository)
        mainViewModel.photoLiveData.observeForever(observer)

        mainViewModel.search(TEST_SEARCH, TEST_PAGE)

        val captor = argumentCaptor<Resource<PhotoListPage>>()
        captor.run {
            verify(observer, times(2)).onChanged(capture())
            val capturedValues = allValues
            assertThat(capturedValues[0], instanceOf(Resource.InProgress::class.java))
            assertThat(capturedValues[1], instanceOf(Resource.Failure::class.java))
            assertEquals((capturedValues[1] as Resource.Failure).errorMessage, TEST_ERROR)
        }
    }

    @Test
    fun `When request succeds, we map into Success object preceeded by InProgress`() {
        val futureMock = mock<Future<PhotoListPage>> { }

        val mockData = PhotoListPage(TEST_SEARCH, TEST_PAGE, listOf(), true)

        val deferredTaskRunner: TaskRunner<PhotoListPage> = mock {
            on {
                this.execute(any())
            }
                .thenAnswer {
                    (it.arguments[0] as Callback<PhotoListPage>).onResponse(mockData)
                    return@thenAnswer FutureTask(futureMock)
                }
        }

        val photoRepository: PhotoRepository = mock {
            on {
                search(TEST_SEARCH, TEST_PAGE)
            }.doReturn(deferredTaskRunner)
        }

        val observer: Observer<Resource<PhotoListPage>> = mock { }

        val mainViewModel = MainViewModel(photoRepository)
        mainViewModel.photoLiveData.observeForever(observer)

        mainViewModel.search(TEST_SEARCH, TEST_PAGE)

        val captor = argumentCaptor<Resource<PhotoListPage>>()
        captor.run {
            verify(observer, times(2)).onChanged(capture())
            val capturedValues = allValues
            assertThat(capturedValues[0], instanceOf(Resource.InProgress::class.java))
            assertThat(capturedValues[1], instanceOf(Resource.Success::class.java))
            assertEquals((capturedValues[1] as Resource.Success).data, mockData)
        }
    }
}
